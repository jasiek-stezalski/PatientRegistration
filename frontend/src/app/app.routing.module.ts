import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserComponent} from './user/user.component';
import {AddUserComponent} from './user/add-user.component';
import {CalendarComponent} from './calendar/calendar.component';
import {PatientCalendarComponent} from './calendar/patient/patientCalendar.component';
import {UrlPermission} from './authentication/urlPermission/url.permission';
import {LoginComponent} from './authentication/components/login/login.component';
import {ProfileComponent} from './authentication/components/profile/profile.component';
import {RegisterComponent} from './authentication/components/register/register.component';

const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  {path: 'calendar', component: CalendarComponent},
  {path: 'patientCalendar', component: PatientCalendarComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [UrlPermission]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},

  // otherwise redirect to profile
  { path: '**', redirectTo: '/login' }
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
