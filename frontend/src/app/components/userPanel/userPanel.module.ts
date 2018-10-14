import {NgModule} from '@angular/core';
import {UserHistoryComponent} from './userHistory/userHistory.component';
import {UsersListComponent} from './usersList/usersList.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {DayPilotModule} from 'daypilot-pro-angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UserVisitsComponent} from './userVisits/userVisits.component';


@NgModule({
  imports: [BrowserModule,
    HttpClientModule,
    DayPilotModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [
    UsersListComponent,
    UserHistoryComponent,
    UserVisitsComponent
  ],
  exports: [UsersListComponent, UserHistoryComponent, UserVisitsComponent],
  providers: []
})
export class UserPanelModule {
}
