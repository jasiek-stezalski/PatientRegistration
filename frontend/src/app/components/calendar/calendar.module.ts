import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {CalendarComponent} from "./calendar.component";
import {DayPilotModule} from "daypilot-pro-angular";
import {HttpClientModule} from "@angular/common/http";
import {VisitModelService} from '../../services/visitModel.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PatientCalendarComponent} from './patient/patientCalendar.component';
import {CreateComponent} from './create/create.component';
import {BookComponent} from "./patient/book/book.component";

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    DayPilotModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    CalendarComponent,
    PatientCalendarComponent,
    CreateComponent,
    BookComponent
  ],
  exports: [CalendarComponent, PatientCalendarComponent],
  providers: [VisitModelService]
})
export class CalendarModule {
}
