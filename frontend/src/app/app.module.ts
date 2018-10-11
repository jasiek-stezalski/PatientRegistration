import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {UserComponent} from './components/user/user.component';
import {AppRoutingModule} from './app.routing.module';
import {UserService} from './services/user.service';
import {HttpClientModule} from '@angular/common/http';
import {AddUserComponent} from './components/user/add/add-user.component';
import {VisitModelService} from './services/visitModel.service';
import {CalendarModule} from './components/calendar/calendar.module';
import {LoginComponent} from './components/authentication/login/login.component';
import {UrlPermission} from './components/authentication/urlPermission/url.permission';
import {RegisterComponent} from './components/authentication/register/register.component';
import {HttpModule} from '@angular/http';
import {UrlPermissionDoctor} from './components/authentication/urlPermission/url.permissionDoctor';
import {VisitService} from './services/visit.services';
import {ClinicService} from './services/clinic.service';
import {UserPanelComponent} from "./components/userPanel/userPanel.component";

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    AddUserComponent,
    LoginComponent,
    RegisterComponent,
    UserPanelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    HttpModule,
    FormsModule,
    CalendarModule,
  ],
  providers: [UserService, VisitModelService, VisitService, ClinicService, UrlPermission, UrlPermissionDoctor],
  bootstrap: [AppComponent]
})
export class AppModule {
}
