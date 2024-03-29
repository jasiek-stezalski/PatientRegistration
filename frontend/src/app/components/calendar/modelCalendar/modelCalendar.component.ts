import {AfterViewInit, Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {VisitModelService} from '../../../services/visitModel.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from './create/create.component';
import {VisitModel} from '../../../models/visitModel.model';
import {User} from '../../../models/user.model';
import {Router} from '@angular/router';

@Component({
  selector: 'modelCalendar-component',
  templateUrl: './modelCalendar.component.html',
  styleUrls: ['./modelCalendar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ModelCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: VisitModel[] = [];
  currentUser: User;

  constructor(public router: Router, private service: VisitModelService) {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
  }

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.service.getVisitModelsInWeekByDoctor(from, to, this.currentUser.id).subscribe(result => {
      this.events = result;

      this.events.forEach(v => {
        if (v.careType === 'Prywatna')
          v.barColor = '#2a8b65';
        else v.barColor = '#487bcc';
      });

    });
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
    businessEndsHour: 19,
    dayBeginsHour: 7,
    dayEndsHour: 20,
    cellDuration: 15,

    onTimeRangeSelected: args => {
      if (args.start < VisitModelService.minimalDate())
        this.calendar.control.message('Model wizyty nie może być utworzony ze wsteczną datą!');
      else
        this.create.show(args);

    },

    eventMoveHandling: 'Update',
    onEventMove: args => {
      let data: VisitModel = {
        id: args.e.id(),
        start: args.newStart.toString(),
        end: args.newEnd.toString()
      };
      this.service.moveVisitModel(data).subscribe(() => {
        this.calendar.control.message('Model wizyty został zmieniony');
      }, err => {
        console.log(err);
        if (err.valueOf().status === 409) {
          console.log(err);
          alert('W tym terminie masz już zaplanowaną wizytę!');
          this.ngAfterViewInit();
        }
      });
    },

    eventDeleteHandling: 'Update',
    onEventDelete: args => {
      if (confirm('Czy na pewno chcesz usunąć ten model wizyty wraz z wszystkimi wizytami?')) {
        this.service.deleteVisitModel(args.e.id()).subscribe(() => {
          this.calendar.control.message('Model wizyty został usunięty');
        });
      } else this.ngAfterViewInit();
    },
  };

  createClosed(args) {
    if (args.result) {
      this.events.push(args.result);
      this.calendar.control.message('Model wizyty został utworzony.');
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

}
