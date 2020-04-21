// Utility function
function Util() { };

/* 
	class manipulation functions
*/
Util.hasClass = function (el, className) {
  if (el.classList) return el.classList.contains(className);
  else return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'));
};

Util.addClass = function (el, className) {
  var classList = className.split(' ');
  if (el.classList) el.classList.add(classList[0]);
  else if (!Util.hasClass(el, classList[0])) el.className += " " + classList[0];
  if (classList.length > 1) Util.addClass(el, classList.slice(1).join(' '));
};

Util.removeClass = function (el, className) {
  var classList = className.split(' ');
  if (el.classList) el.classList.remove(classList[0]);
  else if (Util.hasClass(el, classList[0])) {
    var reg = new RegExp('(\\s|^)' + classList[0] + '(\\s|$)');
    el.className = el.className.replace(reg, ' ');
  }
  if (classList.length > 1) Util.removeClass(el, classList.slice(1).join(' '));
};

Util.toggleClass = function (el, className, bool) {
  if (bool) Util.addClass(el, className);
  else Util.removeClass(el, className);
};

Util.setAttributes = function (el, attrs) {
  for (var key in attrs) {
    el.setAttribute(key, attrs[key]);
  }
};

/* 
  DOM manipulation
*/
Util.getChildrenByClassName = function (el, className) {
  var children = el.children,
    childrenByClass = [];
  for (var i = 0; i < el.children.length; i++) {
    if (Util.hasClass(el.children[i], className)) childrenByClass.push(el.children[i]);
  }
  return childrenByClass;
};

Util.is = function (elem, selector) {
  if (selector.nodeType) {
    return elem === selector;
  }

  var qa = (typeof (selector) === 'string' ? document.querySelectorAll(selector) : selector),
    length = qa.length,
    returnArr = [];

  while (length--) {
    if (qa[length] === elem) {
      return true;
    }
  }

  return false;
};

/* 
	Animate height of an element
*/
Util.setHeight = function (start, to, element, duration, cb) {
  var change = to - start,
    currentTime = null;

  var animateHeight = function (timestamp) {
    if (!currentTime) currentTime = timestamp;
    var progress = timestamp - currentTime;
    var val = parseInt((progress / duration) * change + start);
    element.style.height = val + "px";
    if (progress < duration) {
      window.requestAnimationFrame(animateHeight);
    } else {
      cb();
    }
  };

  //set the height of the element before starting animation -> fix bug on Safari
  element.style.height = start + "px";
  window.requestAnimationFrame(animateHeight);
};

/* 
	Smooth Scroll
*/

Util.scrollTo = function (final, duration, cb) {
  var start = window.scrollY || document.documentElement.scrollTop,
    currentTime = null;

  var animateScroll = function (timestamp) {
    if (!currentTime) currentTime = timestamp;
    var progress = timestamp - currentTime;
    if (progress > duration) progress = duration;
    var val = Math.easeInOutQuad(progress, start, final - start, duration);
    window.scrollTo(0, val);
    if (progress < duration) {
      window.requestAnimationFrame(animateScroll);
    } else {
      cb && cb();
    }
  };

  window.requestAnimationFrame(animateScroll);
};

/* 
  Focus utility classes
*/

//Move focus to an element
Util.moveFocus = function (element) {
  if (!element) element = document.getElementsByTagName("body")[0];
  element.focus();
  if (document.activeElement !== element) {
    element.setAttribute('tabindex', '-1');
    element.focus();
  }
};

/* 
  Misc
*/

Util.getIndexInArray = function (array, el) {
  return Array.prototype.indexOf.call(array, el);
};

Util.cssSupports = function (property, value) {
  if ('CSS' in window) {
    return CSS.supports(property, value);
  } else {
    var jsProperty = property.replace(/-([a-z])/g, function (g) { return g[1].toUpperCase(); });
    return jsProperty in document.body.style;
  }
};

// merge a set of user options into plugin defaults
// https://gomakethings.com/vanilla-javascript-version-of-jquery-extend/
Util.extend = function () {
  // Variables
  var extended = {};
  var deep = false;
  var i = 0;
  var length = arguments.length;

  // Check if a deep merge
  if (Object.prototype.toString.call(arguments[0]) === '[object Boolean]') {
    deep = arguments[0];
    i++;
  }

  // Merge the object into the extended object
  var merge = function (obj) {
    for (var prop in obj) {
      if (Object.prototype.hasOwnProperty.call(obj, prop)) {
        // If deep merge and property is an object, merge properties
        if (deep && Object.prototype.toString.call(obj[prop]) === '[object Object]') {
          extended[prop] = extend(true, extended[prop], obj[prop]);
        } else {
          extended[prop] = obj[prop];
        }
      }
    }
  };

  // Loop through each object and conduct a merge
  for (; i < length; i++) {
    var obj = arguments[i];
    merge(obj);
  }

  return extended;
};

// Check if Reduced Motion is enabled
Util.osHasReducedMotion = function () {
  if (!window.matchMedia) return false;
  var matchMediaObj = window.matchMedia('(prefers-reduced-motion: reduce)');
  if (matchMediaObj) return matchMediaObj.matches;
  return false; // return false if not supported
};

/* 
	Polyfills
*/
//Closest() method
if (!Element.prototype.matches) {
  Element.prototype.matches = Element.prototype.msMatchesSelector || Element.prototype.webkitMatchesSelector;
}

if (!Element.prototype.closest) {
  Element.prototype.closest = function (s) {
    var el = this;
    if (!document.documentElement.contains(el)) return null;
    do {
      if (el.matches(s)) return el;
      el = el.parentElement || el.parentNode;
    } while (el !== null && el.nodeType === 1);
    return null;
  };
}

//Custom Event() constructor
if (typeof window.CustomEvent !== "function") {

  function CustomEvent(event, params) {
    params = params || { bubbles: false, cancelable: false, detail: undefined };
    var evt = document.createEvent('CustomEvent');
    evt.initCustomEvent(event, params.bubbles, params.cancelable, params.detail);
    return evt;
  }

  CustomEvent.prototype = window.Event.prototype;

  window.CustomEvent = CustomEvent;
}

/* 
	Animation curves
*/
Math.easeInOutQuad = function (t, b, c, d) {
  t /= d / 2;
  if (t < 1) return c / 2 * t * t + b;
  t--;
  return -c / 2 * (t * (t - 2) - 1) + b;
};
// File#: _1_countup
// Usage: codyhouse.co/license
(function () {
    var CountUp = function (opts) {
        this.options = Util.extend(CountUp.defaults, opts);
        this.element = this.options.element;
        this.initialValue = parseFloat(this.options.initial);
        this.finalValue = parseFloat(this.element.textContent);
        this.intervalId;
        this.animationTriggered = false;
        // animation will run only on browsers supporting IntersectionObserver
        this.canAnimate = ('IntersectionObserver' in window && 'IntersectionObserverEntry' in window && 'intersectionRatio' in window.IntersectionObserverEntry.prototype);
        initCountUp(this);
    };

    CountUp.prototype.reset = function () { // reset element to its initial value
        if (!this.canAnimate) return;
        window.cancelAnimationFrame(this.intervalId);
        this.element.textContent = this.initialValue;
    };

    CountUp.prototype.restart = function () { // restart element animation
        countUpAnimate(this);
    };

    function initCountUp(countup) {
        if (!countup.canAnimate) { // IntersectionObserver not supported
            countUpShow(countup);
            return;
        }

        // reset countUp for SR
        initCountUpSr(countup);

        // listen for the element to enter the viewport -> start animation
        var observer = new IntersectionObserver(countupObserve.bind(countup), {threshold: [0, 0.1]});
        observer.observe(countup.element);

        // listen to events
        countup.element.addEventListener('countUpReset', function () {
            countup.reset();
        });
        countup.element.addEventListener('countUpRestart', function () {
            countup.restart();
        });
    };

    function countUpShow(countup) { // reveal countup after it has been initialized
        Util.addClass(countup.element.closest('.countup'), 'countup--is-visible');
    };

    function countupObserve(entries, observer) { // observe countup position -> start animation when inside viewport
        if (entries[0].intersectionRatio.toFixed(1) > 0 && !this.animationTriggered) {
            countUpAnimate(this);
        }
    };

    function countUpAnimate(countup) { // animate countup
        countup.element.textContent = countup.initialValue;
        countUpShow(countup);
        window.cancelAnimationFrame(countup.intervalId);
        var currentTime = null;

        function runCountUp(timestamp) {
            if (!currentTime) currentTime = timestamp;
            var progress = timestamp - currentTime;
            if (progress > countup.options.duration) progress = countup.options.duration;
            var val = getValEaseOut(progress, countup.initialValue, countup.finalValue - countup.initialValue, countup.options.duration);
            countup.element.textContent = getCountUpValue(val, countup);
            if (progress < countup.options.duration) {
                countup.intervalId = window.requestAnimationFrame(runCountUp);
            } else {
                countUpComplete(countup);
            }
        };

        countup.intervalId = window.requestAnimationFrame(runCountUp);
    };

    function getCountUpValue(val, countup) { // reset new countup value to proper decimal places+separator
        if (countup.options.decimal) {
            val = parseFloat(val.toFixed(countup.options.decimal));
        } else {
            val = parseInt(val);
        }
        if (countup.options.separator) val = val.toLocaleString('en');
        return val;
    }

    function countUpComplete(countup) { // emit event when animation is over
        countup.element.dispatchEvent(new CustomEvent('countUpComplete'));
        countup.animationTriggered = true;
    };

    function initCountUpSr(countup) { // make sure countup is accessible
        // hide elements that will be animated to SR
        countup.element.setAttribute('aria-hidden', 'true');
        // create new element with visible final value - accessible to SR only
        var srValue = document.createElement('span');
        srValue.textContent = countup.finalValue;
        Util.addClass(srValue, 'sr-only');
        countup.element.parentNode.insertBefore(srValue, countup.element.nextSibling);
    };

    function getValEaseOut(t, b, c, d) {
        t /= d;
        return -c * t * (t - 2) + b;
    };

    CountUp.defaults = {
        element: '',
        separator: false,
        duration: 3000,
        decimal: false,
        initial: 0
    };

    window.CountUp = CountUp;

    //initialize the CountUp objects
    var countUp = document.getElementsByClassName('js-countup');
    if (countUp.length > 0) {
        for (var i = 0; i < countUp.length; i++) {
            (function (i) {
                var separator = (countUp[i].getAttribute('data-countup-sep')) ? countUp[i].getAttribute('data-countup-sep') : false,
                    duration = (countUp[i].getAttribute('data-countup-duration')) ? countUp[i].getAttribute('data-countup-duration') : CountUp.defaults.duration,
                    decimal = (countUp[i].getAttribute('data-countup-decimal')) ? countUp[i].getAttribute('data-countup-decimal') : false,
                    initial = (countUp[i].getAttribute('data-countup-start')) ? countUp[i].getAttribute('data-countup-start') : 0;
                new CountUp({
                    element: countUp[i],
                    separator: separator,
                    duration: duration,
                    decimal: decimal,
                    initial: initial
                });
            })(i);
        }
    }
}());
// File#: _1_main-header
// Usage: codyhouse.co/license
(function() {
    var mainHeader = document.getElementsByClassName('js-main-header')[0];
    if( mainHeader ) {
        var trigger = mainHeader.getElementsByClassName('js-main-header__nav-trigger')[0],
            nav = mainHeader.getElementsByClassName('js-main-header__nav')[0];

        // we'll use these to store the node that needs to receive focus when the mobile menu is closed
        var focusMenu = false;

        //detect click on nav trigger
        trigger.addEventListener("click", function(event) {
            event.preventDefault();
            var ariaExpanded = !Util.hasClass(nav, 'main-header__nav--is-visible');
            //show nav and update button aria value
            Util.toggleClass(nav, 'main-header__nav--is-visible', ariaExpanded);
            trigger.setAttribute('aria-expanded', ariaExpanded);
            if(ariaExpanded) { //opening menu -> move focus to first element inside nav
                nav.querySelectorAll('[href], input:not([disabled]), button:not([disabled])')[0].focus();
            } else if(focusMenu) {
                focusMenu.focus();
                focusMenu = false;
            }
        });
        // listen for key events
        window.addEventListener('keyup', function(event){
            // listen for esc key
            if( (event.keyCode && event.keyCode == 27) || (event.key && event.key.toLowerCase() == 'escape' )) {
                // close navigation on mobile if open
                if(trigger.getAttribute('aria-expanded') == 'true' && isVisible(trigger)) {
                    focusMenu = trigger; // move focus to menu trigger when menu is close
                    trigger.click();
                }
            }
            // listen for tab key
            if( (event.keyCode && event.keyCode == 9) || (event.key && event.key.toLowerCase() == 'tab' )) {
                // close navigation on mobile if open when nav loses focus
                if(trigger.getAttribute('aria-expanded') == 'true' && isVisible(trigger) && !document.activeElement.closest('.js-main-header')) trigger.click();
            }
        });
    }

    function isVisible(element) {
        return (element.offsetWidth || element.offsetHeight || element.getClientRects().length);
    };
}());
// File#: _1_modal-window
// Usage: codyhouse.co/license
(function() {
    var Modal = function(element) {
        this.element = element;
        this.triggers = document.querySelectorAll('[aria-controls="'+this.element.getAttribute('id')+'"]');
        this.firstFocusable = null;
        this.lastFocusable = null;
        this.selectedTrigger = null;
        this.showClass = "modal--is-visible";
        this.initModal();
    };

    Modal.prototype.initModal = function() {
        var self = this;
        //open modal when clicking on trigger buttons
        if ( this.triggers ) {
            for(var i = 0; i < this.triggers.length; i++) {
                this.triggers[i].addEventListener('click', function(event) {
                    event.preventDefault();
                    self.selectedTrigger = event.target;
                    self.showModal();
                    self.initModalEvents();
                });
            }
        }

        // listen to the openModal event -> open modal without a trigger button
        this.element.addEventListener('openModal', function(event){
            if(event.detail) self.selectedTrigger = event.detail;
            self.showModal();
            self.initModalEvents();
        });
    };

    Modal.prototype.showModal = function() {
        var self = this;
        Util.addClass(this.element, this.showClass);
        this.getFocusableElements();
        this.firstFocusable.focus();
        // wait for the end of transitions before moving focus
        this.element.addEventListener("transitionend", function cb(event) {
            self.firstFocusable.focus();
            self.element.removeEventListener("transitionend", cb);
        });
        this.emitModalEvents('modalIsOpen');
    };

    Modal.prototype.closeModal = function() {
        Util.removeClass(this.element, this.showClass);
        this.firstFocusable = null;
        this.lastFocusable = null;
        if(this.selectedTrigger) this.selectedTrigger.focus();
        //remove listeners
        this.cancelModalEvents();
        this.emitModalEvents('modalIsClose');
    };

    Modal.prototype.initModalEvents = function() {
        //add event listeners
        this.element.addEventListener('keydown', this);
        this.element.addEventListener('click', this);
    };

    Modal.prototype.cancelModalEvents = function() {
        //remove event listeners
        this.element.removeEventListener('keydown', this);
        this.element.removeEventListener('click', this);
    };

    Modal.prototype.handleEvent = function (event) {
        switch(event.type) {
            case 'click': {
                this.initClick(event);
            }
            case 'keydown': {
                this.initKeyDown(event);
            }
        }
    };

    Modal.prototype.initKeyDown = function(event) {
        if( event.keyCode && event.keyCode == 27 || event.key && event.key == 'Escape' ) {
            //close modal window on esc
            this.closeModal();
        } else if( event.keyCode && event.keyCode == 9 || event.key && event.key == 'Tab' ) {
            //trap focus inside modal
            this.trapFocus(event);
        }
    };

    Modal.prototype.initClick = function(event) {
        //close modal when clicking on close button or modal bg layer
        if( !event.target.closest('.js-modal__close') && !Util.hasClass(event.target, 'js-modal') ) return;
        event.preventDefault();
        this.closeModal();
    };

    Modal.prototype.trapFocus = function(event) {
        if( this.firstFocusable == document.activeElement && event.shiftKey) {
            //on Shift+Tab -> focus last focusable element when focus moves out of modal
            event.preventDefault();
            this.lastFocusable.focus();
        }
        if( this.lastFocusable == document.activeElement && !event.shiftKey) {
            //on Tab -> focus first focusable element when focus moves out of modal
            event.preventDefault();
            this.firstFocusable.focus();
        }
    }

    Modal.prototype.getFocusableElements = function() {
        //get all focusable elements inside the modal
        var allFocusable = this.element.querySelectorAll('[href], input:not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, [tabindex]:not([tabindex="-1"]), [contenteditable], audio[controls], video[controls], summary');
        this.getFirstVisible(allFocusable);
        this.getLastVisible(allFocusable);
    };

    Modal.prototype.getFirstVisible = function(elements) {
        //get first visible focusable element inside the modal
        for(var i = 0; i < elements.length; i++) {
            if( elements[i].offsetWidth || elements[i].offsetHeight || elements[i].getClientRects().length ) {
                this.firstFocusable = elements[i];
                return true;
            }
        }
    };

    Modal.prototype.getLastVisible = function(elements) {
        //get last visible focusable element inside the modal
        for(var i = elements.length - 1; i >= 0; i--) {
            if( elements[i].offsetWidth || elements[i].offsetHeight || elements[i].getClientRects().length ) {
                this.lastFocusable = elements[i];
                return true;
            }
        }
    };

    Modal.prototype.emitModalEvents = function(eventName) {
        var event = new CustomEvent(eventName, {detail: this.selectedTrigger});
        this.element.dispatchEvent(event);
    };

    //initialize the Modal objects
    var modals = document.getElementsByClassName('js-modal');
    if( modals.length > 0 ) {
        for( var i = 0; i < modals.length; i++) {
            (function(i){new Modal(modals[i]);})(i);
        }
    }
}());
// File#: _1_number-input
// Usage: codyhouse.co/license
(function() {
    var InputNumber = function(element) {
        this.element = element;
        this.input = this.element.getElementsByClassName('js-number-input__value')[0];
        this.min = parseFloat(this.input.getAttribute('min'));
        this.max = parseFloat(this.input.getAttribute('max'));
        this.step = parseFloat(this.input.getAttribute('step'));
        if(isNaN(this.step)) this.step = 1;
        this.precision = getStepPrecision(this.step);
        initInputNumberEvents(this);
    };

    function initInputNumberEvents(input) {
        // listen to the click event on the custom increment buttons
        input.element.addEventListener('click', function(event){
            var increment = event.target.closest('.js-number-input__btn');
            if(increment) updateInputNumber(input, increment);
        });

        // when input changes, make sure the new value is acceptable
        input.input.addEventListener('focusout', function(event){
            var value = parseFloat(input.input.value);
            if( value < input.min ) value = input.min;
            if( value > input.max ) value = input.max;
            // check value is multiple of step
            value = checkIsMultipleStep(input, value);
            if( value != parseFloat(input.input.value)) input.input.value = value;

        });
    };

    function getStepPrecision(step) {
        // if step is a floating number, return its precision
        return (step.toString().length - Math.floor(step).toString().length - 1);
    };

    function updateInputNumber(input, btn) {
        var value = ( Util.hasClass(btn, 'number-input__btn--plus') ) ? parseFloat(input.input.value) + input.step : parseFloat(input.input.value) - input.step;
        if( input.precision > 0 ) value = value.toFixed(input.precision);
        if( value < input.min ) value = input.min;
        if( value > input.max ) value = input.max;
        input.input.value = value;
    };

    function checkIsMultipleStep(input, value) {
        // check if the number inserted is a multiple of the step value
        var remain = (value*10*input.precision)%(input.step*10*input.precision);
        if( remain != 0) value = value - remain;
        if( input.precision > 0 ) value = value.toFixed(input.precision);
        return value;
    };

    //initialize the InputNumber objects
    var inputNumbers = document.getElementsByClassName('js-number-input');
    if( inputNumbers.length > 0 ) {
        for( var i = 0; i < inputNumbers.length; i++) {
            (function(i){new InputNumber(inputNumbers[i]);})(i);
        }
    }
}());
// File#: _1_password
// Usage: codyhouse.co/license
(function () {
    var Password = function (element) {
        this.element = element;
        this.password = this.element.getElementsByClassName('js-password__input')[0];
        this.visibilityBtn = this.element.getElementsByClassName('js-password__btn')[0];
        this.visibilityClass = 'password--text-is-visible';
        this.initPassword();
    };

    Password.prototype.initPassword = function () {
        var self = this;
        //listen to the click on the password btn
        this.visibilityBtn.addEventListener('click', function (event) {
            //if password is in focus -> do nothing if user presses Enter
            if (document.activeElement === self.password) return;
            event.preventDefault();
            self.togglePasswordVisibility();
        });
    };

    Password.prototype.togglePasswordVisibility = function () {
        var makeVisible = !Util.hasClass(this.element, this.visibilityClass);
        //change element class
        Util.toggleClass(this.element, this.visibilityClass, makeVisible);
        //change input type
        (makeVisible) ? this.password.setAttribute('type', 'text') : this.password.setAttribute('type', 'password');
    };

    //initialize the Password objects
    var passwords = document.getElementsByClassName('js-password');
    if (passwords.length > 0) {
        for (var i = 0; i < passwords.length; i++) {
            (function (i) { new Password(passwords[i]); })(i);
        }
    };
}());
// File#: _1_table
// Usage: codyhouse.co/license
(function() {
    var Table = function(element) {
        this.element = element;
        this.innerTable = this.element.getElementsByClassName('js-table__inner')[0];
        this.tableHasStickyHeader = Util.hasClass(this.element, 'table--sticky-header');
        this.header = this.element.getElementsByClassName('js-table__header')[0];
        this.body = this.element.getElementsByClassName('js-table__body')[0];
        this.bodyShadow = this.element.getElementsByClassName('js-table__body-shadow')[0];
        this.scrollingIndicator = this.element.getElementsByClassName('js-table__scrolling-indicator')[0];
        this.scrolling = false;
        this.initTable();
    };

    Table.prototype.initTable = function() {
        var self = this;
        this.resetXScroll(true);
        this.tableHasStickyHeader && this.initStickyHeader();
        Util.removeClass(this.element, 'table--js-is-loading');
        this.initEvents();
    };

    Table.prototype.initEvents = function() {
        var self = this;
        //listen to the scroll of body table -> toggle table right gradient visibility
        this.innerTable.addEventListener('scroll', function(event){
            if(!self.scrolling) {
                self.scrolling = true;
                window.requestAnimationFrame(self.resetXScroll.bind(self, false));
            }
        });

        //listen to the click on the scrolling indicator icon
        this.scrollingIndicator.addEventListener('click', function(event){
            self.innerTable.scrollLeft = self.innerTable.scrollLeft + 250;
        });
    };

    Table.prototype.resetTable = function() {
        this.resetXScroll(true);
        this.tableHasStickyHeader && this.resetStickyHeader();
    };

    Table.prototype.resetXScroll = function(bool) {
        //check if you can scroll horizontally -> toggle table right gradient visibility
        (this.innerTable.offsetWidth < this.body.offsetWidth) ? Util.addClass(this.element, 'table--scrolling-x') : Util.removeClass(this.element, 'table--scrolling-x');
        (this.innerTable.offsetWidth + this.innerTable.scrollLeft < this.body.offsetWidth) ? Util.addClass(this.element, 'table--show-scrolling-indicator') : Util.removeClass(this.element, 'table--show-scrolling-indicator');
        (bool && Util.hasClass(this.element, 'table--show-scrolling-indicator') && this.innerTable.scrollLeft == 0 ) ? Util.removeClass(this.element, 'table--scrolling-x-started') : Util.addClass(this.element, 'table--scrolling-x-started');
        this.scrolling = false;
    };

    Table.prototype.initStickyHeader = function() {
        //if the table has a sticky header -> create header clone and insert it inside the table body
        this.headerClone = this.header.getElementsByTagName('tr')[0].cloneNode(true);
        Util.addClass(this.headerClone, 'table__row--is-hidden');
        this.headerClone.setAttribute('aria-hidden', true); //hide clone from assistive technology
        this.body.insertBefore(this.headerClone, this.body.firstChild);
        this.resetStickyHeader(); //reset table header width
    };

    Table.prototype.resetStickyHeader = function() {
        //reset width of elements in the table header
        var headerCloneChildren = this.headerClone.children,
            headerChildren = this.header.getElementsByTagName('tr')[0].children;
        for(var i = 0; i < headerCloneChildren.length; i++) {
            headerChildren[i].setAttribute('style', 'width:'+headerCloneChildren[i].offsetWidth+'px;');
        }
        //reset position of shadow element
        this.bodyShadow.setAttribute('style', 'height: '+ this.body.offsetHeight +'px; top: '+this.header.offsetHeight+'px;');
    };

    //initialize the Table objects
    var tables = document.getElementsByClassName('js-table');
    if( tables.length > 0 ) {
        var tablesArray = [];
        for( var i = 0; i < tables.length; i++) {
            (function(i){tablesArray.push(new Table(tables[i]));})(i);
        }

        var resizing = false;
        if(window.requestAnimationFrame) {
            //listen for the resize of the window -> toggle table right gradient visibility
            window.addEventListener('resize', function(event){
                if(!resizing) {
                    resizing = true;
                    window.requestAnimationFrame(resetTables);
                }
            });
        }

        function resetTables(){
            tablesArray.forEach(function(element){
                element.resetTable();
            });
            resizing = false;
        };
    };
}());
// File#: _2_full-screen-select
(function() {
    var CustomSelect = function(element) {
        this.element = element;
        this.items = this.element.children;
        initCustomSelect(this);
    };

    function initCustomSelect(select) {
        // arrow navigation
        select.element.addEventListener('keydown', function(event){
            if( event.keyCode && event.keyCode == 40 || event.key && event.key.toLowerCase() == 'arrowdown' ) {
                selectOption(select, 1); // go to next option
            } else if( event.keyCode && event.keyCode == 38 || event.key && event.key.toLowerCase() == 'arrowup' ) {
                selectOption(select, -1); // go to prev option
            }
        });
    };

    function selectOption(select, direction) {
        var focusElement = document.activeElement,
            index = Util.getIndexInArray(select.items, focusElement.closest('li'));
        if(index < 0) return;
        index = index + direction;
        if( index < 0 ) index = select.items.length - 1;
        if( index >= select.items.length) index = 0;
        Util.moveFocus(select.items[index].getElementsByTagName(focusElement.tagName)[0]);
    };

    //initialize the CustomSelect objects
    var customSelect = document.getElementsByClassName('js-full-screen-select__list');
    if( customSelect.length > 0 ) {
        for( var i = 0; i < customSelect.length; i++) {
            (function(i){new CustomSelect(customSelect[i]);})(i);
        }
    }
}());