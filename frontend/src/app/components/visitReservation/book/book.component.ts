import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {VisitModel} from "../../../models/visitModel.model";
import {Router} from "@angular/router";
import {Visit} from "../../../models/visit.model";
import {User} from "../../../models/user.model";
import {VisitService} from "../../../services/visit.services";
import {Clinic} from "../../../models/clinic.model";

@Component({
  selector: 'book-dialog',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  visit: Visit = new Visit();
  user: User = new User();
  clinic: Clinic = new Clinic();

  constructor(private router: Router, private visitService: VisitService) {
    this.visit.user = new User();
    this.visit.visitModel = new VisitModel();
  }

  show(visit: Visit) {
    this.visit = visit;
    this.user = visit.visitModel.user;
    this.clinic = visit.visitModel.clinic;
    this.modal.show();
  }

  submit() {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.bookVisit(this.visit, user.id).subscribe(result => {
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
