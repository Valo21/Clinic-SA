import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgIf} from "@angular/common";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {NgIcon, provideIcons} from "@ng-icons/core";
import {
    faSolidCircleXmark
} from '@ng-icons/font-awesome/solid'
@Component({
  selector: 'app-modal',
  standalone: true,
    imports: [
        NgIf,
        NgIcon
    ],
    providers: [
        provideIcons({faSolidCircleXmark})
    ],
  templateUrl: './modal.component.html',
    animations: [
        trigger('openClosed', [
            // ...
            state('open', style({
                opacity: 1,
                visibility: 'visible'
            })),
            state('closed', style({
                opacity: 0,
                visibility: 'hidden'
            })),
            transition('open => closed', [
                animate('0.5s ease-in')
            ]),
            transition('closed => open', [
                animate('0.5s ease-in')
            ]),
        ]),
    ]
})
export class ModalComponent {
    @Input() state!: boolean;
    @Output() stateChange = new EventEmitter<boolean>();

    public closeModal(){
        this.stateChange.emit(this.state = false)
    }
}
