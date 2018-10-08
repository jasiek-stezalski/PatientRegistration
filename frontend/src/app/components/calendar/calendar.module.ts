import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {ModelCalendarComponent} from "./modelCalendar/modelCalendar.component";
import {DayPilotModule} from "daypilot-pro-angular";
import {HttpClientModule} from "@angular/common/http";
import {VisitModelService} from '../../services/visitModel.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PatientCalendarComponent} from './patientCalendar/patientCalendar.component';
import {CreateComponent} from './modelCalendar/create/create.component';
import {BookComponent} from "./patientCalendar/book/book.component";
import {DoctorCalendarComponent} from './doctorCalendar/doctorCalendar.component';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    DayPilotModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    ModelCalendarComponent,
    PatientCalendarComponent,
    DoctorCalendarComponent,
    CreateComponent,
    BookComponent
  ],
  exports: [ModelCalendarComponent, PatientCalendarComponent, DoctorCalendarComponent],
  providers: [VisitModelService]
})
export class CalendarModule {
}
