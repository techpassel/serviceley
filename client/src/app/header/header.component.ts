import { Component, OnInit } from '@angular/core';
import { faBell, faCartShopping, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  faBell = faBell;
  faCartShopping = faCartShopping;
  faMagnifyingGlass = faMagnifyingGlass;

  constructor() { }

  ngOnInit(): void {
  }

}
