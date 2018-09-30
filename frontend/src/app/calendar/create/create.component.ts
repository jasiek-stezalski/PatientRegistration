import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CalendarService} from '../calendar.service';
import {VisitModel} from '../../models/visitModel.model';
import {Clinic} from '../../models/clinic.model';
import {Router} from '@angular/router';

@Component({
  selector: 'create-dialog',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  form: FormGroup;
  visitModel: VisitModel = new VisitModel;
  clinics: Clinic[];
  minDate: String;

  constructor(private fb: FormBuilder, private router: Router, private service: CalendarService) {
    this.form = this.fb.group({
      start: [''],
      end: [''],
    });
  }

  ngOnInit() {
    this.service.getClinics()
      .subscribe(data => {
        this.clinics = data;
      });
  };

  show(args: any) {
    this.form.setValue({
      start: args.start.toString(),
      end: args.end.toString(),
    });
    this.minDate = ('' + args.start + '').substring(0, 10);
    this.modal.show();
  }

  submit() {
    let data = this.form.getRawValue();
    this.visitModel.start = data.start;
    this.visitModel.end = data.end;
    this.visitModel.clinic = {
      id: this.visitModel.id,
    };
    this.visitModel.user = JSON.parse(sessionStorage.getItem('currentUser'));
    this.service.createVisitModel(this.visitModel).subscribe(result => {
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
