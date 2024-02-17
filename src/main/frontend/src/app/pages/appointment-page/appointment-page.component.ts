import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgForOf, NgOptimizedImage} from "@angular/common";
import {FormsModule, NgForm} from "@angular/forms";
import {ApiService} from "../../api.service";
import {ModalComponent} from "../../features/modal/modal.component";

@Component({
    selector: 'app-appointment-page',
    standalone: true,
    imports: [
        NgForOf,
        FormsModule,
        NgOptimizedImage,
        ModalComponent
    ],
    templateUrl: './appointment-page.component.html'
})
export class AppointmentPageComponent {
    public linkId: string = '';
    public personalIDs: string[] = [
        'DNI'
    ]
    public config: any;
    public isFetching: boolean = false;

    public calendar = {};

    constructor(private router: ActivatedRoute, private apiService: ApiService, private rtr: Router) {
        this.router.queryParams.subscribe(params => {
            this.linkId = params['id']
            this.apiService.getAppointmentInfo(this.linkId).subscribe(
                res => this.config = res.data,
                error =>  rtr.navigate(['/auth'])
            )
        })
    }

    public onSubmit(event: SubmitEvent, appointmentData: NgForm): void {
        event.preventDefault();
        this.isFetching = true;
        this.apiService.createAppointment({
            ...appointmentData.value,
            linkId: this.linkId
        })
            .subscribe(
                res => {
                    alert("Appointment Created")
                    this.isFetching = false;
                },
                res => {
                    alert(res.error.message)
                    this.isFetching = false;
                }
            );
    }
}
