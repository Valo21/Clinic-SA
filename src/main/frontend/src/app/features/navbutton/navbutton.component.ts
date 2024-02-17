import { Component } from '@angular/core';
import {NgIcon, provideIcons} from "@ng-icons/core";
import {faSolidBars} from "@ng-icons/font-awesome/solid";

@Component({
  selector: 'app-navbutton',
  standalone: true,
    imports: [
        NgIcon
    ],
  templateUrl: './navbutton.component.html',
    providers: [
        provideIcons({
            faSolidBars
        })
    ]
})
export class NavbuttonComponent {
    public isOpen: boolean = false;
}
