import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserComponent} from './components/user/user.component';
import {AddUserComponent} from './components/user/add/add-user.component';
import {CalendarComponent} from './components/calendar/calendar.component';
import {PatientCalendarComponent} from './components/calendar/patient/patientCalendar.component';
import {UrlPermission} from './components/authentication/urlPermission/url.permission';
import {LoginComponent} from './components/authentication/login/login.component';
import {HomeComponent} from './components/authentication/home/home.component';
import {RegisterComponent} from './components/authentication/register/register.component';

const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  {path: 'calendar', component: CalendarComponent, canActivate: [UrlPermission]},
  {path: 'patientCalendar', component: PatientCalendarComponent, canActivate: [UrlPermission]},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},

  // otherwise redirect to home
  {path: '**', redirectTo: '/home'}
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
