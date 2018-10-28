import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {DayPilotModule} from "daypilot-pro-angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PatientCalendarComponent} from "./patientCalendar/patientCalendar.component";
import {BookComponent} from "./book/book.component";
import {SearchVisitsComponent} from "./searchVisits/searchVisits.component";
import {VisitService} from "../../services/visit.services";
import { SearchDoctorComponent } from './searchDoctor/searchDoctor.component';


@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    DayPilotModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    PatientCalendarComponent,
    BookComponent,
    SearchVisitsComponent,
    SearchDoctorComponent
  ],
  exports: [PatientCalendarComponent, SearchVisitsComponent, SearchDoctorComponent],
  providers: [VisitService]
})
export class VisitReservationModule {
}
