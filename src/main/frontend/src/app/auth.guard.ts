import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "./auth.service";
import {map} from "rxjs/operators";

export const authGuard: CanActivateFn = (route, state) => {
    const router = inject(Router);
    const authService = inject(AuthService);
    return authService.getUser().pipe(map(user => {
        if (!user){
            router.navigate(['/auth']);
            return false
        }
        authService.$user.next(user);
        return true;
    }));
};
