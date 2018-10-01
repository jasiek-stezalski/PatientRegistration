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

  itemMap: Map<String, Item> = new Map<String, Item>();

  careType: any[] = [
    '-',
    'Publiczna',
    'Prywatna',
  ];

  clinics: Array<String> = [];

  filter: any = {
    careType: '-',
    clinic: '-',
  };

  constructor(private service: CalendarService) {
    let item: Item = {
      isFilter: false,
      name: '',
    };
    this.itemMap.set('careType', item);
    let item2: Item = {
      isFilter: false,
      name: '',
    };
    this.itemMap.set('clinic', item2);
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
        case 'cat1':
          args.data.barColor = '#45818e';
          break;
        case 'cat2':
          args.data.barColor = '#f1c232';
          break;
        case 'cat3':
          args.data.barColor = '#6aa84f';
          break;
      }
      let careType = this.careType.find(c => c.id == args.data.careType);
      if (careType) {
        args.data.areas = [
          {bottom: 5, left: 3, html: careType.name, style: 'color: ' + args.data.barColor}
        ];
      }
    },

    onEventFilter: args => {
      let params = args.filter;
      if (params.text && args.e.text().toLowerCase().indexOf(params.text.toLowerCase()) < 0) {
        args.visible = false;
      }
      if (params.careType !== 'any' && args.e.data.careType !== params.careType) {
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
    this.service.getClinics()
      .subscribe(data => {
        data.forEach(i => {
          this.clinics.push(i.name);
        });
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


  changeCareType(val) {
    this.itemMap.get('careType').name = val;
    this.itemMap.get('careType').isFilter = val != '-';
    this.doFilter();
  }

  changeClinic(val) {
    this.itemMap.get('clinic').name = val;
    this.itemMap.get('clinic').isFilter = val != '-';
    this.doFilter();
  }

  doFilter() {
    this.events = this.events2;

    if (this.itemMap.get('careType').isFilter)
      this.events = this.events.filter(value => value.visitModel.careType == this.itemMap.get('careType').name);
    if (this.itemMap.get('clinic').isFilter)
      this.events = this.events.filter(value => value.visitModel.clinic.name == this.itemMap.get('clinic').name);
  }


  clearFilter() {
    this.filter.careType = '';
    this.filter.clinic = '';
    this.events = this.events2;
    this.itemMap.forEach(i => i.isFilter = false);
    return false;
  }

}

export class Item {
  isFilter: Boolean;
  name: String;
}
