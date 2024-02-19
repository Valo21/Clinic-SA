import {Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import {NavbarComponent} from "./features/navbar/navbar.component";
import {ToastComponent} from "./features/toast/toast.component";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [CommonModule, RouterOutlet, RouterLink, NavbarComponent, ToastComponent],
    templateUrl: './app.component.html',
})
export class AppComponent {

}
