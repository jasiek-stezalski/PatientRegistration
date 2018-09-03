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

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    AddUserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CalendarModule
  ],
  providers: [UserService, CalendarService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
