import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {AuthService} from "../../auth.service";
import {RouterLink} from "@angular/router";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-navbar',
  standalone: true,
    imports: [
        RouterLink,
        AsyncPipe
    ],
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
    public user!: User | null;
    constructor(public auth: AuthService) {
        this.user = this.auth.$user.value
    };
}
