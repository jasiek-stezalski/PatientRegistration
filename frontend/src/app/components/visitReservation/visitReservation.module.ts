import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {DayPilotModule} from "daypilot-pro-angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PatientCalendarComponent} from "./patientCalendar/patientCalendar.component";
import {BookComponent} from "./patientCalendar/book/book.component";
import {SearchVisitsComponent} from "./searchVisits/searchVisits.component";
import {VisitService} from "../../services/visit.services";


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
    SearchVisitsComponent
  ],
  exports: [PatientCalendarComponent, SearchVisitsComponent],
  providers: [VisitService]
})
export class VisitReservationModule {
}
