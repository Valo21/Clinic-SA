import { Component } from '@angular/core';
import {ToastService} from "./toast.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-toast',
  standalone: true,
    imports: [
        NgForOf
    ],
  templateUrl: './toast.component.html'
})
export class ToastComponent {
    constructor(public toastService: ToastService) {}
}
