import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserComponent} from './components/user/user.component';
import {AddUserComponent} from './components/user/add/add-user.component';
import {ModelCalendarComponent} from './components/calendar/modelCalendar/modelCalendar.component';
import {PatientCalendarComponent} from './components/calendar/patientCalendar/patientCalendar.component';
import {LoginComponent} from './components/authentication/login/login.component';
import {RegisterComponent} from './components/authentication/register/register.component';
import {UrlPermissionDoctor} from './components/authentication/urlPermission/url.permissionDoctor';
import {DoctorCalendarComponent} from './components/calendar/doctorCalendar/doctorCalendar.component';

const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  {path: 'modelCalendar', component: ModelCalendarComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'patientCalendar', component: PatientCalendarComponent},
  {path: 'doctorCalendar', component: DoctorCalendarComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},

  // otherwise redirect to home
  {path: '**', redirectTo: '/'}
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
