import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  constructor() { }

  ngOnInit(): void {
  }

  @ViewChild('homeSection', { static: false })
  homeSectionElement !: ElementRef;

  sidebarClosed = () => {
    this.homeSectionElement.nativeElement.classList.toggle('dilate');
  }
}
