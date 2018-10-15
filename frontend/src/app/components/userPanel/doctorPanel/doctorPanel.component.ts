import {AfterViewInit, Component} from '@angular/core';
import {DayPilot} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {User} from '../../../models/user.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-doctorPanel',
  templateUrl: './doctorPanel.component.html',
  styleUrls: ['./doctorPanel.component.css']
})
export class DoctorPanelComponent implements AfterViewInit {

  events: Visit[] = [];
  eventsBase: Visit[] = [];
  actualVisit: Visit = new Visit();
  actualUser: User = new User();

  constructor(private visitService: VisitService, private router: Router) {
  }

  ngAfterViewInit(): void {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getVisitsInWeekByDoctor(DayPilot.Date.today(), DayPilot.Date.today(), user.id).subscribe(result => {
      this.events = result;
      this.eventsBase = result
        .sort((v1, v2) => v1.start > v2.start ? -1 : 1)
        .filter(v => v.text === 'Zajęte')
        .filter(v =>
          (v.start >= DayPilot.Date.today().toString()
            && v.start < DayPilot.Date.today().addDays(1).toString()));
      this.actualVisit = this.eventsBase.pop();
      this.actualUser = this.actualVisit.user;
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
    eventClickHandling: 'Disabled',


  };

  openUserHistory(id: string | number) {
    this.router.navigate(['userHistory/', id]);
  }

  confirmVisit(id: string | number) {
    this.visitService.confirmVisit(id).subscribe(() => {
      alert('Wizyta została Zatwierdzona');
      this.events.find(v => v.id === this.actualVisit.id).text = 'Zakończone';
      if (this.eventsBase.length === 0) {
        this.actualVisit = new Visit();
        this.actualUser = new User();
      } else {
        this.actualVisit = this.eventsBase.pop();
        this.actualUser = this.actualVisit.user;
      }

    });
  }
}
