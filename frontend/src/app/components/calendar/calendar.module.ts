import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {ModelCalendarComponent} from "./modelCalendar/modelCalendar.component";
import {DayPilotModule} from "daypilot-pro-angular";
import {HttpClientModule} from "@angular/common/http";
import {VisitModelService} from '../../services/visitModel.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CreateComponent} from './modelCalendar/create/create.component';
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
    DoctorCalendarComponent,
    CreateComponent,
    InfoComponent,
    BookByDoctorComponent
  ],
  exports: [ModelCalendarComponent, DoctorCalendarComponent],
  providers: [VisitModelService]
})
export class CalendarModule {
}
