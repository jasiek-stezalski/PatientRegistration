import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserComponent} from './user/user.component';
import {AddUserComponent} from './user/add-user.component';
import {CalendarComponent} from './calendar/calendar.component';
import {PatientCalendarComponent} from './calendar/patient/patientCalendar.component';
import {UrlPermission} from './authentication/urlPermission/url.permission';
import {LoginComponent} from './authentication/components/login/login.component';
import {HomeComponent} from './authentication/components/home/home.component';
import {RegisterComponent} from './authentication/components/register/register.component';
import {UrlPermissionDoctor} from "./authentication/urlPermission/url.permissionDoctor";

const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  {path: 'calendar', component: CalendarComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'patientCalendar', component: PatientCalendarComponent, canActivate: [UrlPermission]},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},

  // otherwise redirect to home
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {
}
