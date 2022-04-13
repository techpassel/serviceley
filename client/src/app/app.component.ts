import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  @ViewChild('first', { static: false })
  firstDropdown!: ElementRef;
  @ViewChild('second', { static: false })
  secondDropdown!: ElementRef;
  @ViewChild('third', { static: false })
  thirdDropdown!: ElementRef;
  @ViewChild('sidebar', { static: false })
  sidebarElement!: ElementRef;

  toggleShowMenu = (val: string, currentElement: HTMLElement) => {
    let component = null;
    switch (val) {
      case 'first':
        component = this.firstDropdown
        break;
      case 'second':
        component = this.secondDropdown
        break;
      case 'third':
        component = this.thirdDropdown
        break;
      default:
        break;
    }
    if (component) {
      component.nativeElement.classList.toggle('show_menu');
      console.log(component?.nativeElement.classList);
    }
    currentElement.classList.toggle('transform_arrow');
  }

  toggleSidebar = (elem :HTMLElement) => {
    this.sidebarElement.nativeElement.classList.toggle('close');
    elem.classList.toggle('transform_arrow');
  }
}
