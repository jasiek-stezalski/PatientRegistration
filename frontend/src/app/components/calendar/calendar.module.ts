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
import {InfoComponent} from "./doctorCalendar/info/info.component";
import {BookByDoctorComponent} from "./doctorCalendar/bookByDoctor/bookByDoctor.component";

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
    BookComponent,
    InfoComponent,
    BookByDoctorComponent
  ],
  exports: [ModelCalendarComponent, PatientCalendarComponent, DoctorCalendarComponent],
  providers: [VisitModelService]
})
export class CalendarModule {
}
