import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserComponent} from './user/user.component';
import {AddUserComponent} from './user/add-user.component';
import {CalendarComponent} from './calendar/calendar.component';
import {PatientCalendarComponent} from './calendar/patient/patientCalendar.component';

const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'add', component: AddUserComponent},
  {path: 'calendar', component: CalendarComponent},
  {path: 'patientCalendar', component: PatientCalendarComponent}
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
