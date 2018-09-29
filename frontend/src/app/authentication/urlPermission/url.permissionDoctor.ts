import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {User} from "../../models/user.model";

@Injectable()
export class UrlPermissionDoctor implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let user: User = JSON.parse(localStorage.getItem('currentUser'));
    console.log(user.role);
    if(localStorage.getItem('currentUser') && user.role == 'DOCTOR') {
      // logged in so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
    return false;
  }
}
