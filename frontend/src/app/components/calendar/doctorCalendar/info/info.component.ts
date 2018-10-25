import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {VisitModel} from "../../../../models/visitModel.model";
import {Visit} from "../../../../models/visit.model";
import {User} from "../../../../models/user.model";
import {Clinic} from "../../../../models/clinic.model";

@Component({
  selector: 'info-dialog',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  visit: Visit = new Visit();
  user: User = new User();
  clinic: Clinic = new Clinic();


  constructor() {
    this.visit.user = new User();
    this.visit.visitModel = new VisitModel();
    this.visit.visitModel.user = new User();
  }

  show(visit: Visit) {
    this.visit = visit;
    this.user = visit.user;
    this.clinic = visit.visitModel.clinic;
    this.modal.show();
  }

  submit() {
    this.user = new User();
    this.modal.hide();
  }

  book() {
    this.modal.hide();
  }

  closed(args) {
    this.close.emit(args);
  }

}
