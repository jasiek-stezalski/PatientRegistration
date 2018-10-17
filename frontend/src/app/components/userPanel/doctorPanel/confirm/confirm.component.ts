import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {Router} from "@angular/router";
import {Visit} from "../../../../models/visit.model";
import {VisitService} from "../../../../services/visit.services";

@Component({
  selector: 'confirm-dialog',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  visit: Visit = new Visit();

  constructor(private router: Router, private visitService: VisitService) {
  }

  show(visit: Visit) {
    this.visit = visit;
    this.modal.show();
  }

  submit() {
    this.visitService.confirmVisit(this.visit).subscribe(result => {
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
