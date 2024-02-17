import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {AuthService} from "../../auth.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {NgIcon, provideIcons} from "@ng-icons/core";
import {
    faSolidEnvelope,
    faSolidLock,
    faSolidUser
} from '@ng-icons/font-awesome/solid'

import {
    faBrandGoogle
} from '@ng-icons/font-awesome/brands'
@Component({
    selector: 'app-auth-page',
    standalone: true,
    imports: [
        FormsModule,
        RouterLink,
        NgIcon
    ],
    providers: [
        provideIcons({faSolidEnvelope, faSolidLock, faBrandGoogle, faSolidUser})
    ],
    templateUrl: './auth-page.component.html',
    animations: [
        trigger('shownHidden', [
            // ...
            state('shown', style({
                opacity: 1,
                translate: '0px 0px',
                zIndex: 1
            })),
            state('hidden', style({
                opacity: 1,
                translate: '0px 30px',
                zIndex: -1
            })),
            transition('shown => hidden', [
                animate('0.5s ease-in')
            ]),
            transition('hidden => shown', [
                animate('0.5s ease-in')
            ]),
        ]),
    ]
})
export class AuthPageComponent {
    public showSignUpForm: boolean = false;
    constructor(public auth: AuthService) {};

    public signInSubmit(){

    }

    public signUpSubmit(){

    }

}
