import {AfterViewInit, Component} from '@angular/core';
import {DayPilot} from "daypilot-pro-angular";
import {Visit} from "../../../models/visit.model";
import {VisitService} from "../../../services/visit.services";
import {User} from "../../../models/user.model";

@Component({
  selector: 'app-doctorPanel',
  templateUrl: './doctorPanel.component.html',
  styleUrls: ['./doctorPanel.component.css']
})
export class DoctorPanelComponent implements AfterViewInit {

  events: Visit[] = [];
  eventsBase: Visit[] = [];
  actualVisit: Visit = new Visit();

  constructor(private visitService: VisitService) {
  }

  ngAfterViewInit(): void {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getVisitsInWeekByDoctor(DayPilot.Date.today(), DayPilot.Date.today(), user.id).subscribe(result => {
      this.events = result;
      this.eventsBase = result.sort((v1, v2) => v1.start > v2.start ? 1 : -1).filter(v=>v.start === DayPilot.Date.today().toString());
      this.actualVisit = result.pop();
    });

  }


  config: any = {
    startDate: DayPilot.Date.today(),
    locale: 'pl-pl',
    columnWidthSpec: "Fixed",
    columnWidth: 100,
    headerLevels: 1,
    headerHeight: 30,
    viewType: 'Day',
    businessBeginsHour: 7,
    businessEndsHour: 20,
    dayBeginsHour: 7,
    dayEndsHour: 20,
    cellDuration: 15,

    timeRangeSelectedHandling: "Disabled",
    eventDeleteHandling: "Disabled",
    eventMoveHandling: "Disabled",
    eventResizeHandling: "Disabled",
    eventHoverHandling: "Disabled",
    eventClickHandling: "Disabled",


  }

}
