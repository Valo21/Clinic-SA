import { Routes } from '@angular/router';
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {AuthPageComponent} from "./pages/auth-page/auth-page.component";
import {AppointmentPageComponent} from "./pages/appointment-page/appointment-page.component";
import {AuthLayoutComponent} from "./features/auth-layout/auth-layout.component";
import {authGuard} from "./auth.guard";
import {ProfilePageComponent} from "./pages/profile-page/profile-page.component";
import {NotFoundPageComponent} from "./pages/not-found-page/not-found-page.component";
import {PatientProfilePageComponent} from "./pages/patient-profile-page/patient-profile-page.component";

export const routes: Routes = [
    {
        path: 'auth',
        component: AuthPageComponent,
    },
    {
        path: '',
        component: AuthLayoutComponent,
        canActivate: [authGuard],
        children: [
            {
                path: '',
                component: HomePageComponent,
            },
            {
                path: 'profile',
                component: ProfilePageComponent,
                title: 'Profile'
            },
            {
                path: 'patient',
                component: PatientProfilePageComponent
            }
        ]
    },
    {
        path: 'appointments',
        component: AppointmentPageComponent
    },
    {
        path: '**',
        component: NotFoundPageComponent,
        title: '404 - Not found'
    }
];
