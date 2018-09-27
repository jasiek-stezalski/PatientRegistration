import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {UserComponent} from './user/user.component';
import {AppRoutingModule} from './app.routing.module';
import {UserService} from './user/user.service';
import {HttpClientModule} from '@angular/common/http';
import {AddUserComponent} from './user/add-user.component';
import {CalendarService} from './calendar/calendar.service';
import {CalendarModule} from './calendar/calendar.module';
import {LoginComponent} from './authentication/components/login/login.component';
import {AuthService} from './authentication/services/auth.service';
import {UrlPermission} from './authentication/urlPermission/url.permission';
import {AccountService} from './authentication/services/account.service';
import {HomeComponent} from './authentication/components/home/home.component';
import {RegisterComponent} from './authentication/components/register/register.component';
import {HttpModule} from "@angular/http";
import {UrlPermissionDoctor} from "./authentication/urlPermission/url.permissionDoctor";

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    AddUserComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    HttpModule,
    FormsModule,
    CalendarModule
  ],
  providers: [UserService, CalendarService, AuthService, AccountService, UrlPermission, UrlPermissionDoctor],
  bootstrap: [AppComponent]
})
export class AppModule {
}
