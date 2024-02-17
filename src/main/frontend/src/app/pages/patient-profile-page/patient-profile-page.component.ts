import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {ApiService} from "../../api.service";
import {ActivatedRoute} from "@angular/router";
import {ModalComponent} from "../../features/modal/modal.component";
import {FormsModule, NgForm} from "@angular/forms";
import {AuthService} from "../../auth.service";

function diff_years(dt2: Date, dt1: any) {
    var diff =(dt2.getTime() - dt1.getTime()) / 1000;
    diff /= (60 * 60 * 24);
    return Math.abs(Math.round(diff/365.25));
}
@Component({
  selector: 'app-patient-profile-page',
  standalone: true,
    imports: [
        NgIf,
        ModalComponent,
        NgForOf,
        FormsModule
    ],
  templateUrl: './patient-profile-page.component.html'
})
export class PatientProfilePageComponent implements OnInit {
    public age: number = 0;
    public patient: Partial<Patient> | null = null;
    public currentDate!: String;

    public isPatientHistoryModalOpen: boolean = false;
    constructor(private router: ActivatedRoute, private apiService: ApiService, private auth: AuthService) {
        this.router.queryParams.subscribe(params => {
            const patientId: string = params['id']
            this.apiService.getPatient(patientId).subscribe(
                res => this.patient = res.data
            )
        })
    }

    public handlePHSubmit(event: SubmitEvent, form: NgForm): void {
        event.preventDefault();
        this.apiService.createPatientHistory({
            ...form.value,
            name: `${this.patient?.firstName} ${this.patient?.lastName}`,
            date: this.currentDate,
            patientId: this.patient?.id,
            professionalId: this.auth.$user.value?.id
        }).subscribe(
            res => console.log(res.data)
        )
    }

    ngOnInit() {
        const currentDate: Date = new Date();

        const year = currentDate.getFullYear();
        const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
        const day = currentDate.getDate().toString().padStart(2, '0');

        this.currentDate = year + '-' + month + '-' + day;
    }

    protected readonly Date = Date;
}
