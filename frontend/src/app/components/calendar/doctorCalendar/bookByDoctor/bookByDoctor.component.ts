import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {VisitModel} from "../../../../models/visitModel.model";
import {Router} from "@angular/router";
import {Visit} from "../../../../models/visit.model";
import {User} from "../../../../models/user.model";
import {VisitService} from "../../../../services/visit.services";
import {Clinic} from "../../../../models/clinic.model";

@Component({
  selector: 'bookByDoctor-dialog',
  templateUrl: './bookByDoctor.component.html',
  styleUrls: ['./bookByDoctor.component.css']
})
export class BookByDoctorComponent {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  visit: Visit = new Visit();
  doctor: User = new User();
  patient: User = new User();
  clinic: Clinic = new Clinic();

  constructor(private router: Router, private visitService: VisitService) {
    this.visit.user = new User();
    this.visit.visitModel = new VisitModel();
    this.visit.visitModel.user = new User();

  }

  show(visit: Visit, user: User) {
    this.visit = visit;
    this.doctor = visit.visitModel.user;
    this.patient = user;
    this.clinic = visit.visitModel.clinic;
    this.modal.show();
  }

  submit() {
    this.visitService.bookVisit(this.visit, this.patient.id).subscribe(result => {
      this.visit.text = 'Zajęte';
      this.modal.hide(result);
    });
  }

  cancel() {
    this.modal.hide();
  }

  closed(args) {
    this.close.emit(args);
  }

}
