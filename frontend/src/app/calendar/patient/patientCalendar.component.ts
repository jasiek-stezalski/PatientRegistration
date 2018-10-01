import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {CalendarService} from '../calendar.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from '../create/create.component';
import {Visit} from '../../models/visit.model';
import {User} from '../../models/user.model';

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styleUrls: ['./patientCalendar.component.css']
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: Visit[] = [];
  events2: Visit[] = [];

  ////////

  //mapa key = nazwa pola, value = (info czy używamy tego pola do filtracji + ewentualne dane jak filtrować)

  careType: any[] = [
    "-",
    "Publiczna",
    "Prywatna",
  ];

  filter: any = {
    careType: "-",
    text: "",
    shortOnly: false
  };

  ////////

  constructor(private service: CalendarService) {
  }

  navigatorConfig: any = {
    locale: 'pl-pl',
    showMonths: 3,
    skipMonths: 3,
    selectMode: 'week',
    cellWidth: 30,
    cellHeight: 25,
    dayHeaderHeight: 23,
    titleHeight: 23
  };
  config: any = {
    startDate: DayPilot.Date.today(),
    locale: 'pl-pl',
    viewType: 'Week',
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
      let visit: Visit = this.events.find(a => a.id == args.e.id());
      if (('' + visit.start + '').substring(0, 10) <= ('' + DayPilot.Date.today() + '').substring(0, 10)) {
        this.calendar.control.message('Ten termin jest już nieaktualny!');
      }
      else if (visit.user != null)
        this.calendar.control.message('Ten termin jest już zarezerwowany!');
      else {
        let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
        this.service.bookVisit(args.e.id(), user.id).subscribe(() => {
          this.calendar.control.message('Zostałeś zapisany na wizytę!');
          this.ngAfterViewInit();
        });
      }

    },

    onBeforeEventRender: args => {
      switch (args.data.careType) {
        case "cat1":
          args.data.barColor = "#45818e";
          break;
        case "cat2":
          args.data.barColor = "#f1c232";
          break;
        case "cat3":
          args.data.barColor = "#6aa84f";
          break;
      }
      let careType = this.careType.find(c => c.id == args.data.careType);
      if (careType) {
        args.data.areas = [
          {bottom: 5, left: 3, html: careType.name, style: "color: " + args.data.barColor}
        ];
      }
    },

    onEventFilter: args => {
      var params = args.filter;
      if (params.text && args.e.text().toLowerCase().indexOf(params.text.toLowerCase()) < 0) {
        args.visible = false;
      }
      if (params.careType !== "any" && args.e.data.careType !== params.careType) {
        args.visible = false;
      }
      if (params.shortOnly && args.e.duration() > DayPilot.Duration.days(2)) {
        args.visible = false;
      }
    }


  };

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.service.getVisitsInWeek(from, to).subscribe(result => {
      this.events = result;
      this.events2 = result;
    });
  }

  createClosed(args) {
    if (args.result) {
      this.events.push(args.result);
      this.calendar.control.message('Zostałeś zapisany na wizytę.');
    }
    this.calendar.control.clearSelection();
  }

  navigatePrevious(event): void {
    event.preventDefault();
    this.config.startDate = this.config.startDate.addDays(-7);
  }

  navigateNext(event): void {
    event.preventDefault();
    this.config.startDate = this.config.startDate.addDays(7);
  }

  navigateToday(event): void {
    event.preventDefault();
    this.config.startDate = DayPilot.Date.today();
  }


  //////////

  changeText(val) {
    this.filter.text = val;
    this.applyFilter();
  }

  changeCareType(val) {
    this.events = this.events2;
    if (val != "-")
      this.events = this.events.filter(value => value.visitModel.careType == val);
  }

  changeShort(val) {
    this.filter.shortOnly = val;
    this.applyFilter();
  }

  clearFilter() {
    this.filter.careType = "any";
    this.filter.text = "";
    this.filter.shortOnly = false;
    this.events = this.events2;
    this.applyFilter();
    return false;
  }

  applyFilter() {
    // @ts-ignore
    this.calendar.control.events.filter(this.filter);
  }

  ////////


}
