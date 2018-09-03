import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CalendarService} from '../calendar.service';
import {VisitModel} from '../../models/visitModel.model';
import {Clinic} from '../../models/clinic.model';

@Component({
  selector: 'create-dialog',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  form: FormGroup;
  clinics: Clinic[];

  constructor(private fb: FormBuilder, private service: CalendarService) {
    this.form = this.fb.group({
      text: [''],
      start: [''],
      end: [''],
      endDate: [''],
      dayInterval: [''],
      minuteInterval: [''],
      specialization: [''],
      careType: [''],
      clinic: ['']
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
      text: '',
      start: args.start.toString(),
      end: args.end.toString(),
      endDate: Date.now(),
      dayInterval: '',
      minuteInterval: '',
      specialization: '',
      careType: '',
      clinic: ''
    });
    this.modal.show();
  }

  submit() {
    let data = this.form.getRawValue();

    let params: VisitModel = {
      text: data.text,
      start: data.start,
      end: data.end,
      endDate: data.endDate,
      dayInterval: data.dayInterval,
      minuteInterval: data.minuteInterval,
      specialization: data.specialization,
      careType: data.careType,
      clinic: {
        idClinic: data.clinic,
      },

      // Do zmiany na użytkownika zalogowanego
      user: {
        idUser: 2
      }

    };
    this.service.createVisitModel(params).subscribe(result => {
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
