import {Component, OnDestroy, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {User} from "../../model/User";
import {AuthService} from "../../auth.service";
import {lastValueFrom, Subscription} from "rxjs";

@Component({
  selector: 'app-home-page',
  standalone: true,
    imports: [
        RouterLink
    ],
  templateUrl: './home-page.component.html'
})
export class HomePageComponent {
    public user!: User | null;

    constructor(public auth: AuthService) {
        this.user = this.auth.$user.value
    };

}
