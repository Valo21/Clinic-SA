import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class ApiService {
    private API_URL: string = 'http://localhost:8080/api/v1';

    constructor(private http: HttpClient) { }

    public createAppointment(appointment: any): Observable<ApiResponse<Appointment>> {
       return this.http.post<ApiResponse<Appointment>>('/api/v1/appointments', appointment);
    }

    public createPatientHistory(pHistory: any): Observable<ApiResponse<PatientHistory>> {
        return this.http.post<ApiResponse<PatientHistory>>('/api/v1/patient-history', pHistory);
    }

    public getAppointments(): Observable<ApiResponse<Appointment[]>> {
        return this.http.get<ApiResponse<Appointment[]>>(this.API_URL + '/appointments');
    }

    public getAppointmentsByProfessional(id: string): Observable<ApiResponse<Appointment[]>> {
        return this.http.get<ApiResponse<Appointment[]>>(`api/v1/appointments/professional?id=${id}`);
    }

    public getAppointmentInfo(id: string): Observable<ApiResponse<Appointment[]>> {
        return this.http.get<ApiResponse<Appointment[]>>(`http://localhost:8080/api/v1/appointment-link?id=${id}`);
    }

    public updateLinkConfig(config: any){
        return this.http.put('/api/v1/appointment-link', config)
    }

    public getPatient(id: string){
        return this.http.get<ApiResponse<Patient>>('/api/v1/patients?id=' + id)
    }
}
