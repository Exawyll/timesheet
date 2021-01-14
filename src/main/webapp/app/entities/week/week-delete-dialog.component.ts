import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeek } from 'app/shared/model/week.model';
import { WeekService } from './week.service';

@Component({
  templateUrl: './week-delete-dialog.component.html',
})
export class WeekDeleteDialogComponent {
  week?: IWeek;

  constructor(protected weekService: WeekService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.weekService.delete(id).subscribe(() => {
      this.eventManager.broadcast('weekListModification');
      this.activeModal.close();
    });
  }
}
