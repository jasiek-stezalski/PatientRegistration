import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app.routing.module';
import {UserService} from './services/user.service';
import {HttpClientModule} from '@angular/common/http';
import {VisitModelService} from './services/visitModel.service';
import {CalendarModule} from './components/calendar/calendar.module';
import {LoginComponent} from './components/authentication/login/login.component';
import {UrlPermission} from './components/authentication/urlPermission/url.permission';
import {RegisterComponent} from './components/authentication/register/register.component';
import {HttpModule} from '@angular/http';
import {UrlPermissionDoctor} from './components/authentication/urlPermission/url.permissionDoctor';
import {VisitService} from './services/visit.services';
import {ClinicService} from './services/clinic.service';
import {UserPanelModule} from './components/userPanel/userPanel.module';
import {VisitReservationModule} from "./components/visitReservation/visitReservation.module";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    HttpModule,
    FormsModule,
    CalendarModule,
    VisitReservationModule,
    UserPanelModule
  ],
  providers: [UserService, VisitModelService, VisitService, ClinicService, UrlPermission, UrlPermissionDoctor],
  bootstrap: [AppComponent]
})
export class AppModule {
}
