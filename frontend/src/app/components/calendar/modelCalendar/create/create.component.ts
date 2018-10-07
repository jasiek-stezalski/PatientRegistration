import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {FormBuilder, FormGroup} from '@angular/forms';
import {VisitModelService} from '../../../../services/visitModel.service';
import {VisitModel} from '../../../../models/visitModel.model';
import {Clinic} from '../../../../models/clinic.model';
import {Router} from '@angular/router';
import {User} from '../../../../models/user.model';
import {ClinicService} from '../../../../services/clinic.service';

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
  specialization = this.visitModelService.specialization;
  minDate: String;
  errorMessage: string;

  constructor(private fb: FormBuilder, private router: Router, private visitModelService: VisitModelService, private clinicService: ClinicService) {
    this.form = this.fb.group({
      start: [''],
      end: [''],
    });
  }

  ngOnInit() {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.clinicService.getClinicByIdUser(user.id)
      .subscribe(data => {
        this.clinics = data;
      });
    this.specialization = this.visitModelService.specialization;
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
    if (this.visitModel.endDate < ('' + data.start + '').substring(0, 10)) {
      this.errorMessage = 'Błąd : Termin ostatniej wizyty nie może być wcześniej niż termin pierwszej wizyty!';
    } else {
      this.visitModel.start = data.start;
      this.visitModel.end = data.end;
      if (this.visitModel.price == null)
        this.visitModel.price = 0;
      this.visitModel.clinic = {
        id: this.visitModel.id,
      };
      this.visitModel.id = null;
      this.visitModel.user = JSON.parse(sessionStorage.getItem('currentUser'));
      this.visitModelService.createVisitModel(this.visitModel).subscribe(result => {
        this.modal.hide(result);
      }, err => {
        console.log(err);
        this.errorMessage = 'Błąd : Żadna wizyta nie pasuje do tego modelu!';
      });
    }
  }

  cancel() {
    this.modal.hide();
  }

  closed(args) {
    this.close.emit(args);
  }

}
