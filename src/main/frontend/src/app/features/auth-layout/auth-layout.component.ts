import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth.service";
import {RouterOutlet} from "@angular/router";
import {AsyncPipe} from "@angular/common";
import {NavbarComponent} from "../navbar/navbar.component";
import {NavbuttonComponent} from "../navbutton/navbutton.component";

@Component({
  selector: 'app-auth-loader',
  standalone: true,
    imports: [
        RouterOutlet,
        AsyncPipe,
        NavbarComponent,
        NavbuttonComponent
    ],
  templateUrl: './auth-layout.component.html',
})
export class AuthLayoutComponent {
    constructor(public auth: AuthService) {
    }

}
