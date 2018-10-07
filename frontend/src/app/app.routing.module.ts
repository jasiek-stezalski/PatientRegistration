import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserComponent} from './components/user/user.component';
import {AddUserComponent} from './components/user/add/add-user.component';
import {CalendarComponent} from './components/calendar/calendar.component';
import {PatientCalendarComponent} from './components/calendar/patient/patientCalendar.component';
import {LoginComponent} from './components/authentication/login/login.component';
import {RegisterComponent} from './components/authentication/register/register.component';
import {UrlPermissionDoctor} from './components/authentication/urlPermission/url.permissionDoctor';

const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  {path: 'calendar', component: CalendarComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'patientCalendar', component: PatientCalendarComponent},
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
