import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DayPilot} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {User} from '../../../models/user.model';
import {Router} from '@angular/router';
import {List} from '../../../resources/list.model';
import {isUndefined} from 'util';
import {ConfirmComponent} from "./confirm/confirm.component";
import {UserHistoryComponent} from "../userHistory/userHistory.component";

@Component({
  selector: 'app-doctorPanel',
  templateUrl: './doctorPanel.component.html',
  styleUrls: ['./doctorPanel.component.css']
})
export class DoctorPanelComponent implements AfterViewInit {

  @ViewChild('confirm') confirm: ConfirmComponent;
  @ViewChild('userHistory') userHistory: UserHistoryComponent;

  events: Visit[] = [];
  eventsBase: List<Visit> = new List<Visit>();
  actualVisit: Visit = new Visit();
  actualUser: User = new User();
  isVisible: boolean;

  constructor(private visitService: VisitService, private router: Router) {
  }

  ngAfterViewInit(): void {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getVisitsInWeekByDoctor(DayPilot.Date.today(), DayPilot.Date.today(), user.id).subscribe(result => {
      this.events = result;
      this.eventsBase.addAll(this.filterVisits(result));

      let isVisitToDo: boolean = false;

      for (let i = 0; i < this.eventsBase.size(); i++) {
        if (this.eventsBase.next().text === 'Zajęte') {
          this.actualVisit = this.eventsBase.get();
          this.actualUser = this.actualVisit.user;
          isVisitToDo = true;
          break;
        }
      }
      if (!isVisitToDo) this.eventsBase.setIndex(this.eventsBase.getIndex());
    });

  }

  config: any = {
    startDate: DayPilot.Date.today(),
    locale: 'pl-pl',
    columnWidthSpec: 'Fixed',
    columnWidth: 100,
    headerLevels: 1,
    headerHeight: 30,
    viewType: 'Day',
    businessBeginsHour: 7,
    businessEndsHour: 20,
    dayBeginsHour: 7,
    dayEndsHour: 20,
    cellDuration: 15,

    timeRangeSelectedHandling: 'Disabled',
    eventDeleteHandling: 'Disabled',
    eventMoveHandling: 'Disabled',
    eventResizeHandling: 'Disabled',
    eventHoverHandling: 'Disabled',

    eventClickHandling: 'Enabled',
    onEventClicked: args => {
      for (let i = 0; i < this.eventsBase.size(); i++) {
        if (this.eventsBase.getByIndex(i).id == args.e.id()) {
          this.eventsBase.setIndex(i);
          this.actualVisit = this.eventsBase.get();
          this.actualUser = this.actualVisit.user;
          break;
        }
      }
      this.isVisible = false;
    },


  };

  confirmVisit() {
    if (!isUndefined(this.actualUser.id)) {
      this.confirm.show(this.actualVisit);
    }
  }

  createClosed(args) {
    if (args.result) {
      this.events.find(v => v.id === this.actualVisit.id).text = 'Zakończone';
      this.actualVisit = this.eventsBase.next();
      this.actualUser = this.actualVisit.user;
      alert('Wizyta została zatwierdzona');
    }
    this.isVisible = false;
  }

  filterVisits(data: Visit[]) {
    return data
      .sort((v1, v2) => v1.start > v2.start ? 1 : -1)
      .filter(v => (v.text === 'Zajęte') || v.text === 'Zakończone')
      .filter(v =>
        (v.start >= DayPilot.Date.today().toString()
          && v.start < DayPilot.Date.today().addDays(1).toString()));
  }

  openUserHistory() {
    this.isVisible = !this.isVisible;
    if (!isUndefined(this.actualUser.id))
      this.userHistory.setVisit(this.actualUser.id);
  }

  previousVisit() {
    if (this.eventsBase.size() > 0) {
      this.actualVisit = this.eventsBase.previous();
      this.actualUser = this.actualVisit.user;
      this.isVisible = false;
    }
  }

  nextVisit() {
    if (this.eventsBase.size() > 0) {
      this.actualVisit = this.eventsBase.next();
      this.actualUser = this.actualVisit.user;
      this.isVisible = false;
    }
  }

  bookNextVisit() {
    if (!isUndefined(this.actualUser.id))
      this.router.navigate(['doctorCalendar/', this.actualUser.id]);

  }

}