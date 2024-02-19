import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Toast, ToastType} from "../../model/Toast";

@Injectable({
  providedIn: 'root'
})
export class ToastService {
    public list = new BehaviorSubject<Toast[]>([]);
    constructor() { }

    public addToast(message: string, type: ToastType) {
        const toast: Toast = new Toast(message, type)
        const list: Toast[] = this.list.value;

        list.push(toast);
        const index: number = list.length;

        this.list.next(list)
        setTimeout(() => {
            const updatedList: Toast[] = this.list.value.slice(index);
            this.list.next(updatedList);
        }, 5000)
    }
}
