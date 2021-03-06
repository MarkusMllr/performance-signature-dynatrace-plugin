/*
 * jquery.draggable
 * https://github.com/ducksboard/gridster.js
 *
 * Copyright (c) 2012 ducksboard
 * Licensed under the MIT licenses.
 */

;(function (root, factory) {
    'use strict';
    if (typeof exports === 'object') {
        module.exports = factory(require('jquery'));
    }
    else if (typeof define === 'function' && define.amd) {
        define('gridster-draggable', ['jquery'], factory);
    } else {
        root.GridsterDraggable = factory(root.$ || root.jQuery);
    }

}(this, function ($) {
    'use strict';
    var defaults = {
        items: 'li',
        distance: 1,
        limit: {width: true, height: false},
        offset_left: 0,
        autoscroll: true,
        ignore_dragging: ['INPUT', 'TEXTAREA', 'SELECT', 'BUTTON'], // or function
        handle: null,
        container_width: 0,  // 0 == auto
        move_element: true,
        helper: false,  // or 'clone'
        remove_helper: true
        // drag: function(e) {},
        // start : function(e, ui) {},
        // stop : function(e) {}
    };

    var $window = $(window);
    var dir_map = {x: 'left', y: 'top'};
    var isTouch = !!('ontouchstart' in window);

    var capitalize = function (str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    };

    var idCounter = 0;
    var uniqId = function () {
        return ++idCounter + '';
    };

    /**
     * Basic drag implementation for DOM elements inside a container.
     * Provide start/stop/drag callbacks.
     *
     * @class Draggable
     * @param {HTMLElement} el The HTMLelement that contains all the widgets
     *  to be dragged.
     * @param {Object} [options] An Object with all options you want to
     *        overwrite:
     *    @param {HTMLElement|String} [options.items] Define who will
     *     be the draggable items. Can be a CSS Selector String or a
     *     collection of HTMLElements.
     *    @param {Number} [options.distance] Distance in pixels after mousedown
     *     the mouse must move before dragging should start.
     *    @param {Boolean} [options.limit] Constrains dragging to the width of
     *     the container
     *    @param {Object|Function} [options.ignore_dragging] Array of node names
     *      that sould not trigger dragging, by default is `['INPUT', 'TEXTAREA',
     *      'SELECT', 'BUTTON']`. If a function is used return true to ignore dragging.
     *    @param {offset_left} [options.offset_left] Offset added to the item
     *     that is being dragged.
     *    @param {Number} [options.drag] Executes a callback when the mouse is
     *     moved during the dragging.
     *    @param {Number} [options.start] Executes a callback when the drag
     *     starts.
     *    @param {Number} [options.stop] Executes a callback when the drag stops.
     * @return {Object} Returns `el`.
     * @constructor
     */
    function Draggable(el, options) {
        this.options = $.extend({}, defaults, options);
        this.$document = $(document);
        this.$container = $(el);
        this.$scroll_container = this.options.scroll_container === window ?
            $(window) : this.$container.closest(this.options.scroll_container);
        this.is_dragging = false;
        this.player_min_left = 0 + this.options.offset_left;
        this.player_min_top = 0 + this.options.offset_top;
        this.id = uniqId();
        this.ns = '.gridster-draggable-' + this.id;
        this.init();
    }

    Draggable.defaults = defaults;

    var fn = Draggable.prototype;

    fn.init = function () {
        var pos = this.$container.css('position');
        this.calculate_dimensions();
        this.$container.css('position', pos === 'static' ? 'relative' : pos);
        this.disabled = false;
        this.events();

        $window.bind(this.nsEvent('resize'),
            throttle($.proxy(this.calculate_dimensions, this), 200));
    };

    fn.nsEvent = function (ev) {
        return (ev || '') + this.ns;
    };

    fn.events = function () {
        this.pointer_events = {
            start: this.nsEvent('touchstart') + ' ' + this.nsEvent('mousedown'),
            move: this.nsEvent('touchmove') + ' ' + this.nsEvent('mousemove'),
            end: this.nsEvent('touchend') + ' ' + this.nsEvent('mouseup')
        };

        this.$container.on(this.nsEvent('selectstart'),
            $.proxy(this.on_select_start, this));

        this.$container.on(this.pointer_events.start, this.options.items,
            $.proxy(this.drag_handler, this));

        this.$document.on(this.pointer_events.end, $.proxy(function (e) {
            this.is_dragging = false;
            if (this.disabled) {
                return;
            }
            this.$document.off(this.pointer_events.move);
            if (this.drag_start) {
                this.on_dragstop(e);
            }
        }, this));
    };

    fn.get_actual_pos = function ($el) {
        return $el.position();
    };


    fn.get_mouse_pos = function (e) {
        if (e.originalEvent && e.originalEvent.touches) {
            var oe = e.originalEvent;
            e = oe.touches.length ? oe.touches[0] : oe.changedTouches[0];
        }

        return {
            left: e.clientX,
            top: e.clientY
        };
    };


    fn.get_offset = function (e) {
        e.preventDefault();
        var mouse_actual_pos = this.get_mouse_pos(e);
        var diff_x = Math.round(
            mouse_actual_pos.left - this.mouse_init_pos.left);
        var diff_y = Math.round(mouse_actual_pos.top - this.mouse_init_pos.top);

        var left = Math.round(this.el_init_offset.left +
            diff_x - this.baseX +
            this.$scroll_container.scrollLeft() -
            this.scroll_container_offset_x);
        var top = Math.round(this.el_init_offset.top +
            diff_y - this.baseY +
            this.$scroll_container.scrollTop() -
            this.scroll_container_offset_y);

        if (this.options.limit.width) {
            if (left > this.player_max_left) {
                left = this.player_max_left;
            } else if (left < this.player_min_left) {
                left = this.player_min_left;
            }
        }
        if (this.options.limit.height) {
            if (top > this.player_max_top) {
                top = this.player_max_top;
            } else if (top < this.player_min_top) {
                top = this.player_min_top;
            }
        }

        return {
            position: {
                left: left,
                top: top
            },
            pointer: {
                left: mouse_actual_pos.left,
                top: mouse_actual_pos.top,
                diff_left: diff_x + (this.$scroll_container.scrollLeft() -
                    this.scroll_container_offset_x),
                diff_top: diff_y + (this.$scroll_container.scrollTop() -
                    this.scroll_container_offset_y)
            }
        };
    };


    fn.get_drag_data = function (e) {
        var offset = this.get_offset(e);
        offset.$player = this.$player;
        offset.$helper = this.helper ? this.$helper : this.$player;

        return offset;
    };


    fn.set_limits = function (container_width) {
        container_width || (container_width = this.$container.width());
        this.player_max_left = (container_width - this.player_width +
            -this.options.offset_left);
        this.player_max_top = (this.options.container_height - this.player_height +
            -this.options.offset_top);

        this.options.container_width = container_width;

        return this;
    };


    fn.scroll_in = function (axis, data) {
        var dir_prop = dir_map[axis];

        var area_size = 50;
        var scroll_inc = 30;
        var scrollDir = 'scroll' + capitalize(dir_prop);

        var is_x = axis === 'x';
        var scroller_size = is_x ? this.scroller_width : this.scroller_height;
        var doc_size;
        if (this.$scroll_container === window) {
            doc_size = is_x ? this.$scroll_container.width() :
                this.$scroll_container.height();
        } else {
            doc_size = is_x ? this.$scroll_container[0].scrollWidth :
                this.$scroll_container[0].scrollHeight;
        }
        var player_size = is_x ? this.$player.width() : this.$player.height();

        var next_scroll;
        var scroll_offset = this.$scroll_container[scrollDir]();
        var min_scroll_pos = scroll_offset;
        var max_scroll_pos = min_scroll_pos + scroller_size;

        var mouse_next_zone = max_scroll_pos - area_size;  // down/right
        var mouse_prev_zone = min_scroll_pos + area_size;  // up/left

        var abs_mouse_pos = min_scroll_pos + data.pointer[dir_prop];

        var max_player_pos = (doc_size - scroller_size + player_size);

        if (abs_mouse_pos >= mouse_next_zone) {
            next_scroll = scroll_offset + scroll_inc;
            if (next_scroll < max_player_pos) {
                this.$scroll_container[scrollDir](next_scroll);
                this['scroll_offset_' + axis] += scroll_inc;
            }
        }

        if (abs_mouse_pos <= mouse_prev_zone) {
            next_scroll = scroll_offset - scroll_inc;
            if (next_scroll > 0) {
                this.$scroll_container[scrollDir](next_scroll);
                this['scroll_offset_' + axis] -= scroll_inc;
            }
        }

        return this;
    };


    fn.manage_scroll = function (data) {
        this.scroll_in('x', data);
        this.scroll_in('y', data);
    };


    fn.calculate_dimensions = function () {
        this.scroller_height = this.$scroll_container.height();
        this.scroller_width = this.$scroll_container.width();
    };


    fn.drag_handler = function (e) {
        // skip if drag is disabled, or click was not done with the mouse primary button
        if (this.disabled || e.which !== 1 && !isTouch) {
            return;
        }

        if (this.ignore_drag(e)) {
            return;
        }

        var self = this;
        var first = true;
        this.$player = $(e.currentTarget);

        this.el_init_pos = this.get_actual_pos(this.$player);
        this.mouse_init_pos = this.get_mouse_pos(e);
        this.offsetY = this.mouse_init_pos.top - this.el_init_pos.top;

        this.$document.on(this.pointer_events.move, function (mme) {
            var mouse_actual_pos = self.get_mouse_pos(mme);
            var diff_x = Math.abs(
                mouse_actual_pos.left - self.mouse_init_pos.left);
            var diff_y = Math.abs(
                mouse_actual_pos.top - self.mouse_init_pos.top);
            if (!(diff_x > self.options.distance ||
                diff_y > self.options.distance)
            ) {
                return false;
            }

            if (first) {
                first = false;
                self.on_dragstart.call(self, mme);
                return false;
            }

            if (self.is_dragging === true) {
                self.on_dragmove.call(self, mme);
            }

            return false;
        });

        if (!isTouch) {
            return false;
        }
    };


    fn.on_dragstart = function (e) {
        e.preventDefault();

        if (this.is_dragging) {
            return this;
        }

        this.drag_start = this.is_dragging = true;
        var offset = this.$container.offset();
        this.baseX = Math.round(offset.left);
        this.baseY = Math.round(offset.top);

        if (this.options.helper === 'clone') {
            this.$helper = this.$player.clone()
                .appendTo(this.$container).addClass('helper');
            this.helper = true;
        } else {
            this.helper = false;
        }

        this.scroll_container_offset_y = this.$scroll_container.scrollTop();
        this.scroll_container_offset_x = this.$scroll_container.scrollLeft();
        this.el_init_offset = this.$player.offset();
        this.player_width = this.$player.width();
        this.player_height = this.$player.height();

        this.set_limits(this.options.container_width);

        if (this.options.start) {
            this.options.start.call(this.$player, e, this.get_drag_data(e));
        }
        return false;
    };


    fn.on_dragmove = function (e) {
        var data = this.get_drag_data(e);

        this.options.autoscroll && this.manage_scroll(data);

        if (this.options.move_element) {
            (this.helper ? this.$helper : this.$player).css({
                'position': 'absolute',
                'left': data.position.left,
                'top': data.position.top
            });
        }

        data.prev_position = this.last_position || data.position;

        if (this.options.drag) {
            this.options.drag.call(this.$player, e, data);
        }

        this.last_position = data.position;
        return false;
    };


    fn.on_dragstop = function (e) {
        var data = this.get_drag_data(e);
        this.drag_start = false;

        if (this.options.stop) {
            this.options.stop.call(this.$player, e, data);
        }

        if (this.helper && this.options.remove_helper) {
            this.$helper.remove();
        }

        return false;
    };

    fn.on_select_start = function (e) {
        if (this.disabled) {
            return;
        }

        if (this.ignore_drag(e)) {
            return;
        }

        return false;
    };

    fn.enable = function () {
        this.disabled = false;
    };

    fn.disable = function () {
        this.disabled = true;
    };

    fn.destroy = function () {
        this.disable();

        this.$container.off(this.ns);
        this.$document.off(this.ns);
        $window.off(this.ns);

        $.removeData(this.$container, 'drag');
    };

    fn.ignore_drag = function (event) {
        if (this.options.handle) {
            return !$(event.target).is(this.options.handle);
        }

        if ($.isFunction(this.options.ignore_dragging)) {
            return this.options.ignore_dragging(event);
        }

        if (this.options.resize) {
            return !$(event.target).is(this.options.items);
        }

        return $(event.target).is(this.options.ignore_dragging.join(', '));
    };

    //jQuery adapter
    $.fn.gridDraggable = function (options) {
        return new Draggable(this, options);
    };

    $.fn.dragg = function (options) {
        return this.each(function () {
            if (!$.data(this, 'drag')) {
                $.data(this, 'drag', new Draggable(this, options));
            }
        });
    };

    return Draggable;

}));
