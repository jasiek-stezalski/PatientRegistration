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
import {UsersListComponent} from './components/userPanel/usersList/usersList.component';
import {UrlPermission} from './components/authentication/urlPermission/url.permission';
import {UserHistoryComponent} from './components/userPanel/userHistory/userHistory.component';
import {UserVisitsComponent} from './components/userPanel/userVisits/userVisits.component';
import {DoctorPanelComponent} from "./components/userPanel/doctorPanel/doctorPanel.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'modelCalendar', component: ModelCalendarComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'patientCalendar', component: PatientCalendarComponent},
  {path: 'doctorCalendar', component: DoctorCalendarComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'usersList', component: UsersListComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'userVisits', component: UserVisitsComponent, canActivate: [UrlPermission]},
  {path: 'userHistory', component: UserHistoryComponent, canActivate: [UrlPermission]},
  {path: 'userHistory/:id', component: UserHistoryComponent, canActivate: [UrlPermissionDoctor]},
  {path: 'doctorPanel', component: DoctorPanelComponent, canActivate: [UrlPermissionDoctor]},

  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  // otherwise redirect to /
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
