class PriceRange extends HTMLElement {
    constructor() {
      super();
  
      console.log('Price Range: Constructor', this);
    }
  
    connectedCallback() {
      // Elements
      this.elements = {
        container: this.querySelector('div'),
        track: this.querySelector('div > div'),
        from: this.querySelector('input:first-of-type'),
        to: this.querySelector('input:last-of-type'),
        output: this.querySelector('output')
      }
  
      // Event listeners
      this.elements.from.addEventListener('input', this.handleInput.bind(this));
      this.elements.to.addEventListener('input', this.handleInput.bind(this));
  
      // Properties
      this.currency = (this.hasAttribute('currency') &&
                       this.getAttribute('currency') !== undefined &&
                       this.getAttribute('currency') !== '') ? this.getAttribute('currency') : 'Tr';
            
      // Update the DOM
      this.updateDom();
  
      console.log('Price Range: Connected', this);
    }
  
    disconnectedCallback() {
      delete this.elements;
      delete this.currency;
  
      console.log('Price Range: Disconnected', this);
    }
    
    get from() {
      return parseInt(this.elements.from.value);
    }
    get to() {
      return parseInt(this.elements.to.value);
    }
    
    handleInput(event) {
      if (parseInt(this.elements.to.value) - parseInt(this.elements.from.value) <= 1) {
        if (event.target === this.elements.from) {
          this.elements.from.value = (parseInt(this.elements.to.value) - 1);
        } else if (event.target === this.elements.to) {
          this.elements.to.value = (parseInt(this.elements.from.value) + 1);
        }
      }
  
      // Update the DOM
      this.updateDom();
      
      console.log('Price Range: Updated!!', {
        from: parseInt(this.elements.from.value),
        to: parseInt(this.elements.to.value)
      });
    }
  
    updateDom() {
      this.drawFill();
      this.drawOutput();
    }
  
    drawFill() {
      const percent1 = (this.elements.from.value / this.elements.from.max) * 100,
            percent2 = (this.elements.to.value / this.elements.to.max) * 100;
  
      this.elements.track.style.background = `linear-gradient(to right, var(--track-color) ${percent1}%, var(--track-highlight-color) ${percent1}%, var(--track-highlight-color) ${percent2}%, var(--track-color) ${percent2}%)`;
    }
  
    drawOutput() {
      this.elements.output.textContent = `${this.elements.from.value}${this.currency} - ${this.elements.to.value}${this.currency}`;
    }
  }
  
  customElements.define('price-range', PriceRange);