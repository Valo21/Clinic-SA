import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {AuthService} from "../../auth.service";
import {NgForOf, NgOptimizedImage} from "@angular/common";
import {RouterLink} from "@angular/router";
import {SkeletonScreenComponent} from "../../features/skeleton-screen/skeleton-screen.component";
import {ModalComponent} from "../../features/modal/modal.component";
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {ApiService} from "../../api.service";
import {data} from "autoprefixer";
import {NgIcon, provideIcons} from "@ng-icons/core";
import {faClipboard} from '@ng-icons/font-awesome/regular'
import {faSolidCheck} from '@ng-icons/font-awesome/solid'

@Component({
  selector: 'app-profile-page',
  standalone: true,
    imports: [
        NgOptimizedImage,
        RouterLink,
        NgForOf,
        SkeletonScreenComponent,
        ModalComponent,
        ReactiveFormsModule,
        FormsModule,
        NgIcon
    ],
    providers: [
        provideIcons({faClipboard, faSolidCheck})
    ],
  templateUrl: './profile-page.component.html'
})
export class ProfilePageComponent implements OnInit{
    public isLinkConfigModalOpen: boolean = false;
    public user!: User;
    public copied: boolean = false;
    public linkId: string = '';

    public patients: Partial<Patient>[] | null = null;
    public appointments: Partial<Appointment>[] | null = null;
    protected readonly navigator = navigator;
    protected readonly location = location;

    protected readonly Array = Array;

    constructor(public auth: AuthService, public apiService: ApiService) {
        this.user = this.auth.$user.value!
    };

    async ngOnInit(){
        this.apiService.getAppointmentsByProfessional(this.user.id)
            .subscribe((res) => {
                this.appointments = res.data
                this.linkId = res.message
            })
    }

    public saveLinkConfig(event: SubmitEvent, form: NgForm){
        this.apiService.updateLinkConfig({
            professionalId: this.user.id,
            duration: form.value.duration
        })
            .subscribe(
                data => console.log(data),
                error => console.log(error)
            )
    }

    public formatDate(date: string | Date | undefined){
        if (!date) {
            return ''
        }
        return (new Date(date)).toLocaleDateString('en-us', { year:"numeric", month:"numeric", day:"numeric", hour: "numeric", minute: "numeric"})
    }

    public copyToClipboard(text: string){
        navigator.clipboard.writeText(text).then(r => r);
        this.copied = true;
        setTimeout(() => this.copied = false, 5000);
    }

    protected readonly Date = Date;
    protected readonly data = data;

    public getCurrentDate(): String {
        const currentDate = new Date();

        const year = currentDate.getFullYear();
        const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
        const day = currentDate.getDate().toString().padStart(2, '0');

        return year + '-' + month + '-' + day;
    }
}
